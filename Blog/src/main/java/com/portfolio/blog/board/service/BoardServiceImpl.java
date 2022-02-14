package com.portfolio.blog.board.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.portfolio.blog.board.dto.PaginationDTO;
import com.portfolio.blog.board.entity.BoardEntity;
import com.portfolio.blog.board.repository.BoardRepository;

@Configuration
@Service
public class BoardServiceImpl implements BoardService {

	private BoardRepository boardRepository;

	public BoardServiceImpl(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Override
	public List<BoardEntity> findByBoard(PaginationDTO paginationDTO) throws Exception {
		// TODO Auto-generated method stub

		List<BoardEntity> test = new ArrayList<BoardEntity>();
		test.add(new BoardEntity());

		return test;
	}

	@Override
	@Transactional
	public void saveBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.save(boardEntity);
	}

	@Override
	public void updateBoard(BoardEntity boardEntity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBoard(BoardEntity boardEntity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideBoard(BoardEntity boardEntity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void picktopBoard(BoardEntity boardEntity) throws Exception {
		// TODO Auto-generated method stub

	}

}
