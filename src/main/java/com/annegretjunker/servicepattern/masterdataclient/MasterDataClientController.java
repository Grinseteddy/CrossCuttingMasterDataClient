package com.annegretjunker.servicepattern.masterdataclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MasterDataClientController {

    static Logger logger=LoggerFactory.getLogger(MasterDataClientController.class);

    @GetMapping(value = "/Character/{index}")
    @ResponseBody
    public MasterData masterDataAt(@PathVariable("index") String index) throws Exception {

        try {

            MasterData answer = new MasterData(Integer.valueOf(index));
            logger.info("Master data valid response: "+String.valueOf(answer.SmallLetter)+ " "+String.valueOf(answer.Capital));
            return answer;

        } catch (Exception e) {
            if (e.getClass() == ArrayIndexOutOfBoundsException.class) {
                throw new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Index must be between 0 and 26, index: " + String.valueOf(index));
            }
            else if(e.getClass() == NumberFormatException.class) {
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index must be an integer.");
            }
        }
        return null;
    }
}



