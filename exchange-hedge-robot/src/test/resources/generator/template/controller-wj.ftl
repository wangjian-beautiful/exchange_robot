package ${basePackage}.web;
import ${basePackage}.AbstractController;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ${author}
 * @date  ${date}
 */
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends AbstractController<${modelNameUpperCamel}> {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    private final static Logger logger = LoggerFactory.getLogger(${modelNameUpperCamel}Controller.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

