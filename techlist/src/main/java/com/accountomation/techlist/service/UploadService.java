package com.accountomation.techlist.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	
	public static String[] readRedos(InputStream file) throws IOException {
		
		String[] results = new String[3];
		try(CSVReader reader = new CSVReader(new InputStreamReader(file))) {
			String[] nextLine;
			reader.readNext();
			int counter = 1;
			double timer = 0;
			
			while((nextLine = reader.readNext()) != null) {
				Company company = new Company(nextLine[5]);
				DateMap dateRedo = new DateMap(StringUtil.convertToDate(nextLine[7]));
				DateMap dateOrigin = new DateMap(StringUtil.convertToDate(nextLine[17]));
				
				int daysBetween;
				try {
					daysBetween = DateMap.daysBetween(dateRedo, dateOrigin);
				} catch(NullPointerException e) {
					daysBetween = 0;
				}
				
				RedoType redoType;
				if(!nextLine[1].isEmpty())
					redoType = new RedoType(nextLine[1], nextLine[2]);
				else
					redoType = new RedoType("EMPTY", "No explicit reason");
				
				Redo redo = new Redo(nextLine[6], dateRedo, company, redoType, 
						daysBetween, Double.parseDouble(nextLine[18]), Timestamp.valueOf(LocalDateTime.now()));
				String[] techs;
				if(!nextLine[20].isEmpty())
					techs = StringUtil.readTechID(nextLine[20]);
				else {
					techs = new String[1];
					if(company.getId().equalsIgnoreCase("SSB"))
						techs[0] = "EMPTYA";
					else if(company.getId().equalsIgnoreCase("HOU"))
						techs[0] = "EMPTYH";
					else if(company.getId().equalsIgnoreCase("NJC"))
						techs[0] = "EMPTYT";
				}
				if(techs.length > 1)
					System.out.println(redo.getId() + " tech1: " + techs[0] + " tech2: " + techs[1]);
				else
					System.out.println(redo.getId() + " tech1: " + techs[0]);
				List<TechnicianRedo> techRedos = new ArrayList<>();
				long startTime = System.currentTimeMillis();
				double elapsedTime;
				
				for (String tech : techs) {
					if(!tech.equalsIgnoreCase("")) {
						Technician techO = new Technician(tech, company);
						TechnicianService.saveOrUpdateRedo(techO);
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
			
			results[0] = String.valueOf(timer / 1000) + " seconds";
			results[1] = String.valueOf(counter);
			results[2] = String.valueOf((counter / (timer / 1000)));
		}
		
		return results;
	}

	public static String[] readTechSales(InputStream file) throws IOException {
		
		String[] results = new String[3];
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
						Double.parseDouble(nextLine[5]), Double.parseDouble(nextLine[6]), Timestamp.valueOf(LocalDateTime.now()));
				ArrayList<SaleDetail> saleDetails = new ArrayList<>();
				long startTime = System.currentTimeMillis();
				double elapsedTime;
				
				if(job.getCustType().equalsIgnoreCase("R")) {
					double salesAmountR = 0;
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
							salesAmountR += saleDetail.getAmount();
					if(Double.parseDouble(nextLine[16]) - salesAmountR > 0 && Double.parseDouble(nextLine[16]) != 0)
						saleDetails.add(new SaleDetail(SalesTypeService.retrieve("other"), job,
								Double.parseDouble(nextLine[16]) - salesAmountR));
					
				} else if(job.getCustType().equalsIgnoreCase("C")) {
					double salesAmountC = 0;
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
							salesAmountC += saleDetail.getAmount();
					if(Double.parseDouble(nextLine[15]) - salesAmountC > 0 && Double.parseDouble(nextLine[15]) != 0)
						saleDetails.add(new SaleDetail(SalesTypeService.retrieve("other"), job,
								Double.parseDouble(nextLine[15]) - salesAmountC));
				}
				
				//saving list of sales details to job for hibernate to automatically insert/update
				job.setSaleDetails(saleDetails);
				
				TechnicianService.saveOrUpdate(tech);
				JobService.saveOrUpdate(job);
				
				elapsedTime = System.currentTimeMillis() - startTime;
				timer += elapsedTime;
				System.out.println(counter + ": Order: " + job.getInvoice() + "Time: " + elapsedTime);
				counter++;
			}
			
			results[0] = String.valueOf(timer / 1000) + " seconds";
			results[1] = String.valueOf(counter);
			results[2] = String.valueOf((counter / (timer / 1000)));
		}
		
		return results;
	}
}
