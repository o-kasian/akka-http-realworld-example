package com.realworld.articles.comments;

import com.realworld.articles.ArticleStorage;
import com.realworld.core.User;
import com.realworld.profile.Profile;
import com.realworld.users.UserStorage;
import com.realworld.utils.ISO8601;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final ArticleStorage articleStorage;
    private final CommentStorage commentStorage;
    private final UserStorage userStorage;

    public CommentService(ArticleStorage articleStorage,
                         CommentStorage commentStorage,
                         UserStorage userStorage) {
        this.articleStorage = articleStorage;
        this.commentStorage = commentStorage;
        this.userStorage = userStorage;
    }

    @Transactional
    public Optional<CommentResponse> createComment(String slug, Long userId, CommentRequest comment) {
        return articleStorage.getArticleBySlug(slug)
                .flatMap(article -> userStorage.getUser(article.getAuthorId())
                        .flatMap(user -> commentStorage.createComment(Comment.create(comment.getComment().getBody(), article.getId(), userId))
                                .map(createdComment -> {
                                    boolean isFollowing = userStorage.isFollowing(userId, article.getAuthorId());
                                    return new CommentResponse(new CommentData(
                                            createdComment.getId(),
                                            new ISO8601(createdComment.getCreatedAt()),
                                            new ISO8601(createdComment.getUpdatedAt()),
                                            createdComment.getBody(),
                                            user.getUsername(),
                                            new Profile(user.getUsername(),
                                                    user.getBio(),
                                                    user.getImage(),
                                                    isFollowing)
                                    ));
                                })));
    }

    public CommentsResponse getComments(String slug, Long userId) {
        Optional<Long> articleId = articleStorage.getArticleBySlug(slug).map(article -> article.getId());
        List<Comment> comments = commentStorage.getComments(articleId.orElse(-1L));
        List<User> users = userStorage.getUsersByUserIds(comments.stream()
                .map(Comment::getAuthorId)
                .collect(Collectors.toList()));
        List<Long> follows = userStorage.followingUsers(userId, comments.stream()
                .map(Comment::getAuthorId)
                .collect(Collectors.toList()));

        List<CommentData> commentDataList = users.stream()
                .flatMap(user -> comments.stream()
                        .filter(comment -> comment.getAuthorId().equals(user.getId()))
                        .map(comment -> new CommentData(
                                comment.getId(),
                                new ISO8601(comment.getCreatedAt()),
                                new ISO8601(comment.getUpdatedAt()),
                                comment.getBody(),
                                user.getUsername(),
                                new Profile(user.getUsername(),
                                        user.getBio(),
                                        user.getImage(),
                                        follows.contains(comment.getAuthorId()))
                        )))
                .collect(Collectors.toList());

        return new CommentsResponse(commentDataList);
    }

    @Transactional
    public int deleteComment(String slug, Long id) {
        return commentStorage.deleteComments(id);
    }
}