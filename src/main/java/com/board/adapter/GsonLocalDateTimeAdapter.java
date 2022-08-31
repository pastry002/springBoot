package com.board.adapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/*
 * 게시판에서 타임리프의 Temporals 클래스의 메서드를 사용해서 LocalDateTime 타입의 날짜 형식을 지정
 * REST방식의 처리는 자바스크립트로 되어야하나 자바스크립트에서는 타임리프 클래스의 메서드를 사용할 수 없음
 * Gson 라이브러리가 LocalDateTime 클래스를 제어할 수 있게하는 클래스
 */
public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
	
	@Override
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src));
	}
	
	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
	}
}
