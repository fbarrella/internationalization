package com.nettops.helloworld.service.user;

import com.nettops.helloworld.exception.ResourceNotFoundException;
import com.nettops.helloworld.model.user.User;
import com.nettops.helloworld.repository.user.UserRepository;
import com.nettops.helloworld.utils.LocalHttpUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(@NonNull User user) throws Exception {
        User created = repository.save(user);
        return created;
    }

    public User findByIdAndTreatHeaderValues(@NonNull Long id,
                                             @NonNull HttpServletRequest request,
                                             @NonNull HttpServletResponse response) throws ResourceNotFoundException {
        LocalHttpUtils.addTraceIdToResponseHeaders(request, response);
        return findById(id);
    }

    public User findById(@NonNull Long id) throws ResourceNotFoundException {
        try{
            Optional<User> user = repository.findById(id);
            return user.get();
        }catch (NoSuchElementException  e){
            throw new ResourceNotFoundException("Exception.notFound", User.class.getSimpleName(), id);
        }
    }

    public User update(@NonNull User updatedUser, @NonNull Long id) throws Exception {
        User user = findById(id);
        updatedUser.setId(user.getId());
        updatedUser = repository.save(updatedUser);
        return updatedUser;
    }

    public void delete(@NonNull Long id) throws Exception {
        User user = findById(id);
        repository.delete(user);
    }

}
