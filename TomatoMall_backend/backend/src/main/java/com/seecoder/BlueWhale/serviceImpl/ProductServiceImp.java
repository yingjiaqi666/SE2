package com.seecoder.BlueWhale.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.repository.ProductRepository;
import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.vo.ProductVO;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductVO> getAll(){
        return productRepository.findAll().stream().map(Product::toVO).collect(Collectors.toList());
    }

    @Override
    public ProductVO getById(int id) {
        return productRepository.findById(id).toVO();
    }

    @Override
    public Boolean updateProduct(ProductVO productVO) {
        int id = productVO.getId();
        Product target = productRepository.findById(id);
        if(target == null){
            return false;
        }
        target.setTitle(productVO.getTitle());
        target.setPrice(productVO.getPrice());
        target.setRate(productVO.getRate());
        if(productVO.getDiscription()!=null){
            target.setDiscription(productVO.getDiscription());
        }
        if(productVO.getCover()!=null){
            target.setCover(productVO.getCover());
        }
        if(productVO.getDetail()!=null){
            target.setDetail(productVO.getDetail());
        }
        if(productVO.getSpecifications()!=null){
            target.setSpecifications(productVO.getSpecifications());
        }
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

}