package com.sparta.bootcamp.java_2_example.domain.purchase.service.impl;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseCommandService;
import com.sparta.bootcamp.java_2_example.domain.purchase.service.PurchaseQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.purchase.service
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService implements PurchaseQueryService, PurchaseCommandService {

}
