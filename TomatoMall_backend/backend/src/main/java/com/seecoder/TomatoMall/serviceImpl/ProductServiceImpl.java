package com.seecoder.TomatoMall.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.TomatoMall.po.Product;
import com.seecoder.TomatoMall.po.Stockpile;
import com.seecoder.TomatoMall.repository.ProductRepository;
import com.seecoder.TomatoMall.repository.StockpileRepository;
import com.seecoder.TomatoMall.service.ProductService;
import com.seecoder.TomatoMall.vo.ProductVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductServiceImpl implements ProductService{
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
        Product product = productRepository.findById(id);

        if(product == null){
            throw TomatoMallException.productNotFound();
        }
        return product.toVO();
    }

    @Override
    public Boolean updateProduct(ProductVO productVO) {
        int id = productVO.getId();
        Product target = productRepository.findById(id);
        if(target == null){
            throw TomatoMallException.productNotFound();
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
            target.getSpecifications().clear();
            for (Specification spec : productVO.getSpecifications()) {
                spec.setProduct(target);
                target.getSpecifications().add(spec);
            }
        }
        productRepository.save(target);
        return true;
    }

    @Override
    public ProductVO addProduct(ProductVO productVO) {
        Product newProduct = productVO.toPO();
        Product savePO = productRepository.save(newProduct);
        Stockpile newStockpile = new Stockpile();
        newStockpile.setProductid(String.valueOf(newProduct.getId()));
        stockpileRepository.save(newStockpile);
        return savePO.toVO();
    }

    @Override
    public Boolean deleteProduct(String id) {
        int idInt = Integer.parseInt(id);
        if(productRepository.findById(idInt)==null){
            throw TomatoMallException.productNotFound();
        }
        stockpileRepository.deleteById(stockpileRepository.findByProductid(id).getId());
        productRepository.deleteById(idInt);
        return true;
    }

    @Override
    public Boolean changeStockpile(String productid, int amount) {
        Stockpile target = stockpileRepository.findByProductid(productid);
        if(target==null){
            throw TomatoMallException.productNotFound();
        }
        target.setAmount(amount);
        stockpileRepository.save(target);
        return true;
    }

    @Override
    public Stockpile getStockpile(String productid) {
        return stockpileRepository.findByProductid(productid);
    }

    @Override
    public String getCover(String productid){
        return productRepository.findById(Integer.parseInt(productid)).getCover();
    }
}