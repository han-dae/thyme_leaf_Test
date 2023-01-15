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
        return "fileBoard/detail/";
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
            String fileName = files.getOriginalFilename(); // 사용자 컴에 저장된 파일명 그대로
            // 확장자
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile; // DB에 저장할 파일 고유명
            String destinationFileName;
            // 절대경로 설정 안해주면 지 맘대로 들어가버려서 절대경로 박아주었습니다.
            String fileUrl = "/Users";

            do { // 우선 실행 후
                 // 고유명 생성
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName); // 합쳐주기
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs(); // 디렉토리
            files.transferTo(destinationFile);

            fboardService.fileBoardInsert(board);

            FileVO file = new FileVO();
            file.setB_no(board.getB_no());
            file.setFilename(destinationFileName);
            file.setFileoriginname(fileName);
            file.setFileurl(fileUrl);

            fboardService.fileInsert(file);
        }

        return "forward:/fileBoard/list"; // 객체 재사용
    }

    @RequestMapping("/update/{b_no}")
    private String fileBoardUpdateForm(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", fboardService.fileBoardDetail(b_no));
        return "fileBoard/update";
    }

    @RequestMapping("/updateProc")
    private String fileBoardUpdateProc(@ModelAttribute FileBoardVO board) {

        fboardService.fileBoardUpdate(board);
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
