package fi.netum.csc.service.dto.aoe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class MyPageable extends PageRequest {

    public MyPageable() {
        super(0,0, Sort.by("relevance"));
    };

    public MyPageable(int page, int size, Sort sort) {
        super(page,size, sort);
    }


}
