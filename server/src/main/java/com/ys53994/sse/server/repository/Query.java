package com.ys53994.sse.server.repository;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data(staticConstructor = "of")
public class Query {

  private final Collection<String> tokens;

}
