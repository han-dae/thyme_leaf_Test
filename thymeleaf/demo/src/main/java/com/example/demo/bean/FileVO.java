package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
    private int f_no; // 파일번호
    private int b_no; 
    private String filename; 
    private String fileoriginname; // 파일 이름
    private String fileurl; //  경로
}
