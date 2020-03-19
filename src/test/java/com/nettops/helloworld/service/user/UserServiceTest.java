package com.nettops.helloworld.service.user;

import com.nettops.helloworld.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService service = Mockito.mock(UserService.class);

    private User createUserObject() {
        return User.builder()
                .createdAt(new Date())
                .updatedAt(new Date())
                .id(1L)
                .email("felipe.netto@itau-unibanco.com.br")
                .name("Felipe Barrella Netto")
                .age(23)
                .build();
    }

    @Test
    public void crudTest() throws Exception {
        User user = createUserObject();

        /** create **/
        when(service.create(any(User.class))).then(returnsFirstArg());
        User created = service.create(user);
        assertThat(created).isNotNull();

        /** read **/
        when(service.findById(1L)).thenReturn(user);
        User retrieved = service.findById(1L);
        assertThat(retrieved).isNotNull();

        /** update **/
        retrieved.setName("Fulano da Silva");
        when(service.update(retrieved, 1L)).thenReturn(retrieved);
        User updated = service.update(retrieved, 1L);
        assertThat(updated.getName()).isEqualTo("Fulano da Silva");

        /** delete **/
        service.delete(1L);
        verify(service, times(1)).delete(1L);
    }
}