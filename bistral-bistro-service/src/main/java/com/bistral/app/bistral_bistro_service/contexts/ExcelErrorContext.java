package com.bistral.app.bistral_bistro_service.contexts;

import com.bistral.app.bistral_bistro_service.utils.ExcelErrorLogger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class ExcelErrorContext {
    private final ThreadLocal<ExcelErrorLogger> logger = ThreadLocal.withInitial(ExcelErrorLogger::new);
}

