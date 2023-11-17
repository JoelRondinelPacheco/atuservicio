package com.atuservicio.atuservicio.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atuservicio.atuservicio.entities.Comment;
import com.atuservicio.atuservicio.repositories.CommentRepository;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/deleteComment/{idComment}")
    public String deteleComment(@PathVariable("idComment") String idComment,
            @RequestParam String idSupplier, ModelMap model) {
        System.out.println("Controlador moderator");
        Optional<Comment> commentDelete = this.commentRepository.findById(idComment);
        Comment comment = new Comment();
        if (commentDelete.isPresent()) {
            comment = commentDelete.get();
            this.commentRepository.delete(comment);
        }

        return "redirect:/supplier/workPreview/" + idSupplier;

    }
}