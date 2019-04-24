package com.annegretjunker.servicepattern.masterdataclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MasterDataClientController {

    static Logger logger=LoggerFactory.getLogger(MasterDataClientController.class);

    private MasterData answer;

    @GetMapping(value = "/Character/{index}")
    @ResponseBody
    public MasterData masterDataAt(@PathVariable("index") String index) throws Exception {

        try {

            answer = new MasterData(Integer.valueOf(index));
            logger.info("Master data valid response: "+String.valueOf(answer.smallLetter)+ " "+String.valueOf(answer.capital));
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

    @PostMapping(value = "/refresh/{kindofalphabet}")
    public String RefreshAlphabet(@PathVariable int kindofalphabet) throws Exception {
        if (answer==null)
            answer=new MasterData(0);
        try {
            if (kindofalphabet == 0) {
                answer.initializeAlphabet(MasterData.KindOfAlphabet.SMALLLETTER);
            } else {
                answer.initializeAlphabet(MasterData.KindOfAlphabet.CAPITALLETTER);
            }
            return String.valueOf(answer.smallAlphabet) + " " + String.valueOf(answer.capitalAlphabet);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "New master data not found: " + answer.smallAlphabet + " " + answer.capitalAlphabet);
        }
    }
}



