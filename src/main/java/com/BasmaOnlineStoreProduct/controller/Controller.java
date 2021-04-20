package com.BasmaOnlineStoreProduct.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.BasmaOnlineStoreProduct.beans.Categorie;
import com.BasmaOnlineStoreProduct.beans.Produit;
import com.BasmaOnlineStoreProduct.repository.CategorieRepository;
import com.BasmaOnlineStoreProduct.repository.ProduitRepository;


@RepositoryRestController
public class Controller {
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@ResponseBody
	@RequestMapping(path="/categories", method=RequestMethod.POST)
	public ResponseEntity<Object> saveCategory(@RequestPart("categorie") Categorie categorie, 
			@RequestParam("image") MultipartFile image) throws IOException {
		String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
		String nomImage = Long.toString(Calendar.getInstance().getTimeInMillis()) + extension;
		categorie.setImage(nomImage);
		categorieRepository.save(categorie);
		
		File convFile = new File("./src/main/resources/categorie/images/"+nomImage); 
		convFile.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(convFile);
		fileOutputStream.write(image.getBytes());
		fileOutputStream.close();
		return new ResponseEntity<>("ajouter terminer avec succes", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(path="/produits", method=RequestMethod.POST)
	public ResponseEntity<Object> saveProduct(@RequestPart("produit") Produit produit, 
			@RequestParam("images") MultipartFile images[], @RequestParam("categorie_id") Long categorie_id) throws IOException {
		Long nomImage = Calendar.getInstance().getTimeInMillis();
		System.out.println(categorie_id);
		if (images.length>=4 && images.length<=8) {
			for (MultipartFile image : images) {
				String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
				produit.getImages().add(Long.toString(nomImage) + extension);
				
				File convFile = new File("./src/main/resources/produit/images/"+nomImage+extension); 
				convFile.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(convFile);
				fileOutputStream.write(image.getBytes());
				fileOutputStream.close();
				nomImage++;
			}
			produit.setCategorie(categorieRepository.getOne(categorie_id));
			produitRepository.save(produit);
			return new ResponseEntity<>("ajouter terminer avec succes", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("nombre d'image doit etre entre 4 et 8", HttpStatus.LENGTH_REQUIRED);
		}
		
	}

}
