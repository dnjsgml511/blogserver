package com.portfolio.blog.data.service.impl;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.service.BoardService;

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
		return boardRepository.findByTitleLikeAndTopAndHide("%" + search + "%", 0, 0, pageable);
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
	@Transactional
	public void updateBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.save(boardEntity);
	}

	/**
	 * @apiNote 데이터 삭제
	 * @param BoardEntity
	 */
	@Override
	@Transactional
	public void deleteBoard(BoardEntity boardEntity) throws Exception {
		boardRepository.delete(boardEntity);
	}

	/**
	 * @apiNote 데이터 숨기기
	 * @param BoardEntity
	 */
	@Override
	@Transactional
	public void hideBoard(BoardEntity boardEntity) throws Exception {
		BoardEntity target = boardRepository.findById(boardEntity.getIdx()).get();
		target.setHide(boardEntity.getHide());

		boardRepository.save(target);
	}

	/**
	 * @apiNote 데이터 상단 고정
	 * @param BoardEntity
	 */
	@Override
	@Transactional
	public void topPickBoard(BoardEntity boardEntity) throws Exception {
		BoardEntity target = boardRepository.findById(boardEntity.getIdx()).get();
		target.setTop(boardEntity.getTop());

		boardRepository.save(target);
	}

	/**
	 * @apiNote 상단 데이터
	 * @param BoardEntity
	 */
	@Override
	public Page<BoardEntity> findByTopBoard(Pageable pageable) throws Exception {
		return boardRepository.findByTop(1, pageable);
	}
}
