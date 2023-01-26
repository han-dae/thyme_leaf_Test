package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.bean.FileVO;
import com.example.demo.service.FileBoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/fileBoard")
public class FileBoardController {

    @Autowired
    FileBoardService fboardService;

    @RequestMapping("/list")
    private String fileBoardList(Model model, HttpServletRequest request) {

        List<FileBoardVO> testList = new ArrayList<>();
        testList = fboardService.getFileBoardList();
        model.addAttribute("testlist", testList);
        return "fileBoard/list";
    }

    @RequestMapping("/detail/{b_no}")
    private String fileBoardDetail(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", fboardService.fileBoardDetail(b_no));
        return "fileBoard/detail";
    }

    @RequestMapping("/insert")
    private String fileBoardInsertForm(@ModelAttribute FileBoardVO board) {
        return "fileBoard/insert";
    }

    @RequestMapping("/insertProc")
    private String fileBoardInsertProc(@ModelAttribute FileBoardVO board, @RequestPart MultipartFile files,
            HttpServletRequest request) throws IllegalStateException, IOException, Exception {

        if (files.isEmpty()) {
            fboardService.fileBoardInsert(board);

        } else {
            String fileName = files.getOriginalFilename();
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile;
            String destinationFileName;

            String fileUrl = "C:/Users/holy han/thymeleaf/demo/src/main/resources/static/file";

            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);

            fboardService.fileBoardInsert(board);

            FileVO file = new FileVO();
            file.setB_no(board.getB_no());
            file.setFilename(destinationFileName);
            file.setFileoriginname(fileName);
            file.setFileurl(fileUrl);

            fboardService.fileInsert(file);
        }

        return "forward:/fileBoard/list";
    }

    @RequestMapping("/update/{b_no}")
    private String fileBoardUpdateForm(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", fboardService.fileBoardDetail(b_no));
        return "fileBoard/update";
    }

    @RequestMapping("/updateProc")
    private String fileBoardUpdateProc(@ModelAttribute FileBoardVO board, @RequestPart MultipartFile files)
            throws IllegalStateException, IOException {
        files.getOriginalFilename();
        fboardService.fileBoardUpdate(board);
        String fileName = files.getOriginalFilename();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        File destinationFile;
        String destinationFileName;

        String fileUrl = "C:/Users/holy han/thymeleaf/demo/src/main/resources/static/file";

        destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
        destinationFile = new File(fileUrl + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        FileVO file = new FileVO();
        file.setB_no(board.getB_no());
        file.setFilename(destinationFileName);
        file.setFileoriginname(fileName);
        file.setFileurl(fileUrl);

        fboardService.fileUpdate(file);

        int bno = board.getB_no();
        String b_no = Integer.toString(bno);
        return "redirect:/fileBoard/detail/" + b_no;
    }

    @RequestMapping("/delete/{b_no}")
    private String fileBoardDelete(@PathVariable("b_no") int b_no) {
        fboardService.fileBoardDelete(b_no);
        return "redirect:/fileBoard/list";
    }
}
