package com.accountomation.techlist.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.accountomation.techlist.dao.JobService;
import com.accountomation.techlist.dao.RedoService;
import com.accountomation.techlist.dao.RedoTypeService;
import com.accountomation.techlist.dao.SalesTypeService;
import com.accountomation.techlist.dao.TechnicianService;
import com.accountomation.techlist.model.Company;
import com.accountomation.techlist.model.DateMap;
import com.accountomation.techlist.model.Job;
import com.accountomation.techlist.model.Redo;
import com.accountomation.techlist.model.RedoType;
import com.accountomation.techlist.model.SaleDetail;
import com.accountomation.techlist.model.Technician;
import com.accountomation.techlist.model.TechnicianRedo;
import com.accountomation.techlist.util.StringUtil;
import com.opencsv.CSVReader;

public class UploadService {
	
	public static void readRedos(InputStream file) throws IOException {
		
		try(CSVReader reader = new CSVReader(new InputStreamReader(file))) {
			String[] nextLine;
			reader.readNext();
			int counter = 1;
			double timer = 0;
			
			while((nextLine = reader.readNext()) != null) {
				Company company = new Company(nextLine[5]);
				DateMap date = new DateMap(StringUtil.convertToDate(nextLine[7]));
				RedoType redoType = new RedoType(nextLine[1], nextLine[2]);
				Redo redo = new Redo(nextLine[6], date, company, redoType);
				String[] techs = StringUtil.readTechID(nextLine[20]);
				System.out.println(redo.getId() + " tech1: " + techs[0] + " tech2: " + techs[1]);
				List<TechnicianRedo> techRedos = new ArrayList<>();
				long startTime = System.currentTimeMillis();
				double elapsedTime;
				
				for (String tech : techs) {
					if(!tech.equalsIgnoreCase("")) {
						Technician techO = new Technician(tech);
						techRedos.add(new TechnicianRedo(redo, techO));
					}
				}
				
				if(!techRedos.isEmpty())
					redo.setTechRedos(techRedos);
				
				RedoTypeService.saveOrUpdate(redoType);
				RedoService.save(redo);
				
				elapsedTime = System.currentTimeMillis() - startTime;
				timer += elapsedTime;
				System.out.println(counter + ": Order: " + redo.getId() + "Time: " + elapsedTime);
				counter++;
			}
			
			System.out.println("Total time for upload (seconds): " + timer / 1000);
			System.out.println("Total number of records: " + counter);
			System.out.println("Total records / second: " + (counter / (timer / 1000)));
		}
	}

	public static void readTechSales(InputStream file) throws IOException {
		
		try(CSVReader reader = new CSVReader(new InputStreamReader(file))) {
			String[] nextLine;
			reader.readNext();
			int counter = 1;
			double timer = 0;
			
			while((nextLine = reader.readNext()) != null) {
				Company company = new Company(nextLine[2]);
				Technician tech = new Technician(nextLine[9], nextLine[11], nextLine[10],
						company);
				DateMap date = new DateMap(StringUtil.convertToDate(nextLine[7]));
				Job job = new Job(
						nextLine[8], date, company, tech, nextLine[14],
						Double.parseDouble(nextLine[5]), Double.parseDouble(nextLine[6]));
				ArrayList<SaleDetail> saleDetails = new ArrayList<>();
				long startTime = System.currentTimeMillis();
				double elapsedTime;
				
				if(job.getCustType().equalsIgnoreCase("R")) {
					int saleCounterR = 0;
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("carpt_cln"), job, 
							Double.parseDouble(nextLine[17])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("furn_cln"), job, 
							Double.parseDouble(nextLine[18])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("prot"), job, 
							Double.parseDouble(nextLine[19])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("deod"), job, 
							Double.parseDouble(nextLine[20])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("spot"), job, 
							Double.parseDouble(nextLine[21])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("rake"), job, 
							Double.parseDouble(nextLine[22])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("mat"), job, 
							Double.parseDouble(nextLine[23])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("odo"), job, 
							Double.parseDouble(nextLine[24])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("wext"), job, 
							Double.parseDouble(nextLine[25])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("crep"), job, 
							Double.parseDouble(nextLine[26])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("guar"), job, 
							Double.parseDouble(nextLine[27])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("tile"), job, 
							Double.parseDouble(nextLine[28])));
					
					for(SaleDetail saleDetail : saleDetails)
						if(saleDetail.getAmount() != 0)
							saleCounterR++;
					if(saleCounterR == 0 && Double.parseDouble(nextLine[16]) != 0)
						saleDetails.add(new SaleDetail(SalesTypeService.retrieve("other"), job,
								Double.parseDouble(nextLine[16])));
					
					
				} else if(job.getCustType().equalsIgnoreCase("C")) {
					int saleCounterC = 0;
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("carpt_cln"), job, 
							Double.parseDouble(nextLine[29])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("furn_cln"), job, 
							Double.parseDouble(nextLine[30])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("prot"), job, 
							Double.parseDouble(nextLine[31])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("deod"), job, 
							Double.parseDouble(nextLine[32])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("spot"), job, 
							Double.parseDouble(nextLine[33])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("rake"), job, 
							Double.parseDouble(nextLine[34])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("mat"), job, 
							Double.parseDouble(nextLine[35])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("odo"), job, 
							Double.parseDouble(nextLine[36])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("wext"), job, 
							Double.parseDouble(nextLine[37])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("crep"), job, 
							Double.parseDouble(nextLine[38])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("guar"), job, 
							Double.parseDouble(nextLine[39])));
					saleDetails.add(new SaleDetail(SalesTypeService.retrieve("tile"), job, 
							Double.parseDouble(nextLine[40])));
					
					for(SaleDetail saleDetail : saleDetails) 
						if(saleDetail.getAmount() != 0)
							saleCounterC++;
					if(saleCounterC == 0 && Double.parseDouble(nextLine[16]) != 0)
						saleDetails.add(new SaleDetail(SalesTypeService.retrieve("other"), job,
								Double.parseDouble(nextLine[16])));
				}
				
				//saving list of sales details to job for hibernate to automatically insert/update
				job.setSaleDetail(saleDetails);
				
				TechnicianService.saveOrUpdate(tech);
				JobService.saveOrUpdate(job);
				
				elapsedTime = System.currentTimeMillis() - startTime;
				timer += elapsedTime;
				System.out.println(counter + ": Order: " + job.getInvoice() + "Time: " + elapsedTime);
				counter++;
			}
			
			System.out.println("Total time for upload (seconds): " + timer / 1000);
			System.out.println("Total number of records: " + counter);
			System.out.println("Total records / second: " + (counter / (timer / 1000)));
		}
	}
}
