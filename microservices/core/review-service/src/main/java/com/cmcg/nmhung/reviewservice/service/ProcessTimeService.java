package com.cmcg.nmhung.reviewservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProcessTimeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessTimeService.class);

    @Value("${service.defaultMinMs}")
    private int minMs;

    @Value("${service.defaultMaxMs}")
    private int maxMs;

    public void setDefaultProcessingTime(int minMs, int maxMs) {

        if (minMs < 0) {
            minMs = 0;
        }
        if (maxMs < minMs) {
            maxMs = minMs;
        }

        this.minMs = minMs;
        this.maxMs = maxMs;
        LOG.info("Set response time to {} - {} ms.", this.minMs, this.maxMs);
    }

    public int calculateProcessingTime() {
        int processingTimeMs = minMs + (new Random().nextInt() * (maxMs - minMs));
        LOG.debug("Return calculated processing time: {} ms", processingTimeMs);
        return processingTimeMs;
    }
}
