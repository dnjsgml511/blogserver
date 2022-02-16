package com.portfolio.blog.board.service;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portfolio.blog.board.entity.BoardEntity;
import com.portfolio.blog.board.repository.BoardRepository;

@Configuration
@Service
public class BoardServiceImpl implements BoardService {

	private BoardRepository boardRepository;

	public BoardServiceImpl(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	/**
	 * @apiNote 게시판 데이터 가져오기
	 * @param Pageable
	 */
	@Override
	public Page<BoardEntity> findByBoard(Pageable pageable, String search) throws Exception {
		return boardRepository.findByTitleLike("%" + search + "%", pageable);
	}

	/**
	 * @apiNote 데이터 저장
	 * @param BoardEntity
	 */
	@Override
	@Transactional
	public void saveBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.save(boardEntity);
	}

	/**
	 * @apiNote 데이터 수정
	 * @param BoardEntity
	 */
	@Override
	public void updateBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.save(boardEntity);
	}

	/**
	 * @apiNote 데이터 삭제
	 * @param BoardEntity
	 */
	@Override
	public void deleteBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.delete(boardEntity);
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
