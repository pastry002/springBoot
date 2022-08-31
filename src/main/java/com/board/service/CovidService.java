package com.board.service;

import java.io.IOException;
import java.util.List;

import com.board.domain.CovidDTO;

public interface CovidService {

	public List<CovidDTO> getCovidDatas() throws IOException;
}