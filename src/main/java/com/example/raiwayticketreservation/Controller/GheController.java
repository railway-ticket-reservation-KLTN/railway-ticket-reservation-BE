package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/v1")
public class GheController {

    @Autowired
    private GheService gheService;

    @Operation(summary = "Chọn chỗ",
            description = "Khi khách hàng chọn vào một ghế bất kỳ thì API này sẽ thực thi " +
                    "để thực hiện việc đặt chỗ tạm thời trong thời gian 600s",
            tags = "API Chọn trả chỗ" )
    @PostMapping("/datcho")
    public ResponseEntity datChoTam(@RequestBody TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        return gheService.datChoTam(trangThaiGheRequest);
    }

    @Operation(summary = "Nhả chỗ",
            description = "Sau khi khách hàng chọn lại ghế đã chọn hoặc hủy bỏ vé trong giỏ " +
                    "thì API này thực thi để xóa chỗ đã đặt",
            tags = "API Chọn trả chỗ")
    @RequestMapping(value = "/tracho", method = RequestMethod.DELETE)
    public ResponseEntity traCho(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.xoaDatChoTam(trangThaiGheRequest))
            return new ResponseEntity<>(true, HttpStatus.OK) ;
        return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
