package com.example.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;

@RestController
public class UserController {
    @Autowired private UserRepository userRepository;

    @GetMapping({"/users"})
    public PagingResponse getAllUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "item_per_page", defaultValue = "10") int itemPerPage) {
        PagingResponse pagingResponse = new PagingResponse(page, itemPerPage);
        List<UsersResponse> usersResponseList = new ArrayList<>();
        Page<User> users = userRepository.findAll(PageRequest.of(page, itemPerPage));
        for (User user : users.getContent()) {
            usersResponseList.add(new UsersResponse(user.getId(), user.getName(), user.getAge()));
        }
        pagingResponse.setUsersResponse(usersResponseList);
        return pagingResponse;
    }

    @GetMapping("/users/{id}")
    public UsersResponse getUserById(@PathVariable int id) {
        Optional<User> users = userRepository.findById(id);
        return new UsersResponse(users.get().getId(), users.get().getName(), users.get().getAge());
    }

    @PostMapping("/users")
    public UsersResponse createNewUser(@RequestBody NewUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user = userRepository.save(user);
        return new UsersResponse(user.getId(), user.getName(), user.getAge());
    }

    private List<UsersResponse> getSampleUsers(int num) {
        List<UsersResponse> ur = new ArrayList<>();
        for (int i = 1; i < num + 1; i++) {
            ur.add(new UsersResponse(i, "User " + i, new Random().nextInt(100) + 1));
        }
        return ur;
    }
}
