package com.empik.jagee.domain.processors;

import com.empik.jagee.domain.queries.IQuery;
import com.empik.jagee.domain.queries.results.ErrorQueryResult;
import com.empik.jagee.domain.sideeffects.ISideEffect;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryProcessorTest {

    private QueryProcessor processor;

    @Mock
    private ISideEffect sideEffect1;

    @Mock
    private ISideEffect sideEffect2;

    @Mock
    private ISideEffect sideEffect3;

    @BeforeEach
    public void setup()
    {
        processor = new QueryProcessor();

        processor.setProcessors(Collections.emptyList());
        processor.setSideEffects(Collections.emptyList());
    }

    @Test
    public void processorShouldReturnErrorQueryResult_when_unknownQuery()
    {
        // Act
        val result = processor.processQuery(new UnknownQuery());

        // Assert
        assertTrue(result instanceof ErrorQueryResult);
        assertEquals("Bad query", ((ErrorQueryResult)result).getMessage());
    }

    @Test
    public void processorShouldRunAllMatchingSideEffects()
    {
        // Arrange
        processor.setSideEffects(Arrays.asList(sideEffect1, sideEffect2, sideEffect3));

        when(sideEffect1.hasSideEffect(any(SomeQuery.class), any()))
                .thenReturn(true);

        when(sideEffect2.hasSideEffect(any(SomeQuery.class), any()))
                .thenReturn(true);

        when(sideEffect3.hasSideEffect(any(SomeQuery.class), any()))
                .thenReturn(false);

        // Act
        processor.processQuery(new SomeQuery());

        // Assert
        verify(sideEffect1, times(1)).doSideEffect(any(), any());
        verify(sideEffect2, times(1)).doSideEffect(any(), any());
        verify(sideEffect3, never()).doSideEffect(any(), any());
    }

    private class UnknownQuery implements IQuery {}

    private class SomeQuery implements IQuery {}
}