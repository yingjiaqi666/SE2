package com.seecoder.BlueWhale.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.Stockpile;
import com.seecoder.BlueWhale.repository.ProductRepository;
import com.seecoder.BlueWhale.repository.StockpileRepository;
import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.vo.ProductVO;

@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockpileRepository stockpileRepository;

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
        if(productVO.getDescription()!=null){
            target.setDescription(productVO.getDescription());
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
        return true;
    }

    @Override
    public ProductVO addProduct(ProductVO productVO) {
        Product newProduct = productVO.toPO();
        productRepository.save(newProduct);
        Stockpile newStockpile = new Stockpile();
        newStockpile.setProductid(String.valueOf(newProduct.getId()));
        stockpileRepository.save(newStockpile);
        return productVO;
    }

    @Override
    public Boolean deleteProduct(String id) {
        int idInt = Integer.parseInt(id);
        if(productRepository.findById(idInt)==null){
            return false;
        }
        stockpileRepository.deleteById(stockpileRepository.findByProductid(id).getId());
        productRepository.deleteById(idInt);
        return true;
    }

    @Override
    public Boolean changeStockpile(String productid, int amount) {
        Stockpile target = stockpileRepository.findByProductid(productid);
        if(target==null){
            return false;
        }
        target.setAmount(amount);
        stockpileRepository.save(target);
        return true;
    }

    @Override
    public Stockpile getStockpile(String productid) {
        return stockpileRepository.findByProductid(productid);
    }

}