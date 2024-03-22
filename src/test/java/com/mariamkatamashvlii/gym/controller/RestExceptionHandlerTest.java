package com.mariamkatamashvlii.gym.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainingTypeFetchException;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RestExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class RestExceptionHandlerTest {
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Test
    void testUserNotCreatedException() {
        ResponseEntity<String> actualUserNotCreatedExceptionResult = restExceptionHandler
                .userNotCreatedException(new UserNotCreatedException("An error occurred"));
        assertEquals("An error occurred", actualUserNotCreatedExceptionResult.getBody());
        assertEquals(400, actualUserNotCreatedExceptionResult.getStatusCodeValue());
        assertTrue(actualUserNotCreatedExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testUserNotCreatedException2() {
        UserNotCreatedException e = mock(UserNotCreatedException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualUserNotCreatedExceptionResult = restExceptionHandler.userNotCreatedException(e);
        verify(e).getMessage();
        assertEquals("Not all who wander are lost", actualUserNotCreatedExceptionResult.getBody());
        assertEquals(400, actualUserNotCreatedExceptionResult.getStatusCodeValue());
        assertTrue(actualUserNotCreatedExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testTrainingTypeFetchException() {
        ResponseEntity<String> actualTrainingTypeFetchExceptionResult = restExceptionHandler
                .trainingTypeFetchException(new TrainingTypeFetchException("0123456789ABCDEF", new Throwable()));
        assertEquals("0123456789ABCDEF", actualTrainingTypeFetchExceptionResult.getBody());
        assertEquals(400, actualTrainingTypeFetchExceptionResult.getStatusCodeValue());
        assertTrue(actualTrainingTypeFetchExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testTrainingTypeFetchException2() {
        TrainingTypeFetchException e = mock(TrainingTypeFetchException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualTrainingTypeFetchExceptionResult = restExceptionHandler.trainingTypeFetchException(e);
        verify(e).getMessage();
        assertEquals("Not all who wander are lost", actualTrainingTypeFetchExceptionResult.getBody());
        assertEquals(400, actualTrainingTypeFetchExceptionResult.getStatusCodeValue());
        assertTrue(actualTrainingTypeFetchExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleTraineeNotFoundException() {
        ResponseEntity<String> actualHandleTraineeNotFoundExceptionResult = restExceptionHandler
                .handleTraineeNotFoundException(new TraineeNotFoundException("An error occurred"));
        assertEquals("An error occurred", actualHandleTraineeNotFoundExceptionResult.getBody());
        assertEquals(400, actualHandleTraineeNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleTraineeNotFoundExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleTraineeNotFoundException2() {
        TraineeNotFoundException e = mock(TraineeNotFoundException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleTraineeNotFoundExceptionResult = restExceptionHandler
                .handleTraineeNotFoundException(e);
        verify(e).getMessage();
        assertEquals("Not all who wander are lost", actualHandleTraineeNotFoundExceptionResult.getBody());
        assertEquals(400, actualHandleTraineeNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleTraineeNotFoundExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleTrainerNotFoundException() {
        ResponseEntity<Object> actualHandleTrainerNotFoundExceptionResult = restExceptionHandler
                .handleTrainerNotFoundException(new TrainerNotFoundException("An error occurred"));
        assertEquals("An error occurred", actualHandleTrainerNotFoundExceptionResult.getBody());
        assertEquals(400, actualHandleTrainerNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleTrainerNotFoundExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleTrainerNotFoundException2() {
        TrainerNotFoundException e = mock(TrainerNotFoundException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<Object> actualHandleTrainerNotFoundExceptionResult = restExceptionHandler
                .handleTrainerNotFoundException(e);
        verify(e).getMessage();
        assertEquals("Not all who wander are lost", actualHandleTrainerNotFoundExceptionResult.getBody());
        assertEquals(400, actualHandleTrainerNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleTrainerNotFoundExceptionResult.getHeaders().isEmpty());
    }
}
