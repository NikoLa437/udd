package com.example.udd.services.impl;

import com.example.udd.elasticsearch.entities.ApplicationIndex;
import com.example.udd.entities.Application;
import com.example.udd.entities.CV;
import com.example.udd.entities.City;
import com.example.udd.entities.CoverLetter;
import com.example.udd.entities.DTO.ApplicationInputDTO;
import com.example.udd.repository.ApplicationRepository;
import com.example.udd.repository.CityRepository;
import com.example.udd.repository.ElasticSearchRepository;
import com.example.udd.services.impl.utils.FileUtilServiceImpl;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationServiceImpl {
    private static final String CV_FOLDER_PATH = "/cv";
    private static final String CL_FOLDER_PATH = "/cover_letter";
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ElasticSearchRepository elasticSearchRepository;

    @Autowired
    private FileUtilServiceImpl fileUtilService;
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public UUID createApplication (ApplicationInputDTO applicationDTO, MultipartFile cv, MultipartFile cl) throws IOException {
        Application application = this.mapApplicationDTOtoApplication(applicationDTO);

        String pathToCV = fileUtilService.saveFile(cv, CV_FOLDER_PATH, application.getId().toString());
        String cvContent = fileUtilService.extractPDFFile(pathToCV);
        CV newCv = new CV(pathToCV);

        String pathToCL = fileUtilService.saveFile(cl, CL_FOLDER_PATH, application.getId().toString());
        String cLContent = fileUtilService.extractPDFFile(pathToCL);
        CoverLetter newCL = new CoverLetter(pathToCL);

        application.setCv(newCv);
        application.setCoverLetter(newCL);

        applicationRepository.save(application);

        ApplicationIndex applicationIndex = mapApplicationToApplicationIndex(application, cvContent, cLContent);

        elasticSearchRepository.save(applicationIndex);

        return application.getId();
    }

    private ApplicationIndex mapApplicationToApplicationIndex(Application application, String cvContent, String cLContent) {
        return new ApplicationIndex(application.getId().toString(), application.getFirstName(), application.getLastName(), application.getQualifications(),
                cvContent, cLContent, new GeoPoint(application.getCity().getLatitude(), application.getCity().getLongitude()),
                application.getCity().getName());

    }

    private Application mapApplicationDTOtoApplication(ApplicationInputDTO dto){
        CV newCv= new CV("test");
        CoverLetter newCL = new CoverLetter("test1");
        return new Application(dto.FirstName,dto.LastName, cityRepository.getById(dto.CityId), dto.Qualifications, newCv, newCL, dto.Username, dto.Password,dto.Email, dto.Address, dto.PhoneNumber);
    }
}
