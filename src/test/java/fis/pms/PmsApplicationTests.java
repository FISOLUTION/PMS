package fis.pms;

import fis.pms.controller.dto.DocuDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PmsApplicationTests {

	@Test
	void contextLoads() {
		DocuDto docuDto = new DocuDto();
		String asd = docuDto.getF_efilenum();
		System.out.println(asd);
	}

}
