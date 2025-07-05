package com.example.demo.controller;

import com.example.demo.Dto.CreateOwnerDto;
import com.example.demo.entity.Owner;
import com.example.demo.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping(path = "/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner createOwner (@RequestBody CreateOwnerDto newOwner){
        System.out.println(newOwner);
        return ownerService.createOwner(newOwner);
    }


}
