package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.bean.FileVO;

public interface FileBoardService {
    List<FileBoardVO> getFileBoardList();

    FileBoardVO fileBoardDetail(int b_no);

    int fileBoardInsert(FileBoardVO fileBoard);

    int fileBoardUpdate(FileBoardVO fileBoard);

    int fileBoardDelete(int bno);

    int fileInsert(FileVO file);
<<<<<<< HEAD

    int fileUpdate(FileVO file);
=======
    FileVO fileDetail(int b_no);
    int fileDelete(int b_no);
>>>>>>> 0c60248b9e466aaeac0298f5458fa447c90d9c5f
}
