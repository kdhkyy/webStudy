package com.web;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.domain.enums.BoardType;


import com.web.repository.BoardRepository;
import com.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest()
@RunWith(SpringRunner.class)
public class JpaMappingTests {
    private final String boardTestTitle = "테스트123";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init(){
        User user = userRepository.save(User.builder()
                .name("havi")
                .password("1234")
                .email(email)
                .createdDate(LocalDateTime.now())
                .build());

        boardRepository.save(Board.builder()
                .title(boardTestTitle)
                .subTitle("서브 타이틀")
                .content("콘텐츠")
                .boardType(BoardType.free)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .user(user).build());
    }

    @Test
    public void tests(){
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("havi"));
        assertThat(user.getPassword(), is("1234"));
        assertThat(user.getEmail(), is(email));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getSubTitle(), is("서브 타이틀"));
        assertThat(board.getContent(), is("콘텐츠"));
        assertThat(board.getBoardType(), is(BoardType.free));
    }
}
