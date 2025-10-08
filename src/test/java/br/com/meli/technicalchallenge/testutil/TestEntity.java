package br.com.meli.technicalchallenge.testutil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple test entity for testing BaseJsonRepository behavior.
 * Used exclusively in unit tests to validate repository functionality
 * without depending on domain entities.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private String id;
    private String name;
    private Integer value;
}