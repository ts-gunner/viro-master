package com.forty.viro.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SseEvent {

    private String event;

    private String data;
}
