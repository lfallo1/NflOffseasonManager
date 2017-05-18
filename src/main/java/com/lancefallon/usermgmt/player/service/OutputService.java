package com.lancefallon.usermgmt.player.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.common.util.AppConstants;
import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.model.AppProperties;
import com.lancefallon.usermgmt.player.model.Player;

@Service
public class OutputService {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AppProperties appProperties;
	
	public String outputToExcel(OAuth2Authentication auth) throws IllegalArgumentException, IllegalAccessException, DatabaseException{
		List<Player> players = playerService.findAll(auth);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFFont bold = this.createFont(workbook, "Arial", true, (short)12);
		XSSFFont regular = this.createFont(workbook, "Arial", false, (short)10);
		XSSFCellStyle boldStyle = workbook.createCellStyle();
		boldStyle.setFont(bold);
		XSSFCellStyle regularStyle = workbook.createCellStyle();
		regularStyle.setFont(regular);
		
		List<Integer> years = players.stream().map(p->p.getYear()).distinct().collect(Collectors.toList());
		for(Integer year : years){
	        XSSFSheet sheet = workbook.createSheet(year.toString());
	        List<Player> filteredPlayers = players.stream().filter(p->p.getYear().equals(year)).collect(Collectors.toList());
	
	        int rowNum = 0;
	
	        //header row
	        Field[] fields = Player.class.getDeclaredFields();
	        Row row = sheet.createRow(rowNum++);
	        
	        int colNum = 0;
	        for(Field field : fields){
	        	if(AppConstants.JAVA_PRIMITIVE_WRAPPERS.contains(field.getType().toString())){
	                Cell cell = row.createCell(colNum++);
	                String value = field.getName();
	                cell.setCellValue(value);
	                cell.setCellStyle(boldStyle);
	        	}
	        }
	        
	        //data rows
	        for (Player player : filteredPlayers) {
	            row = sheet.createRow(rowNum++);          
	            colNum = 0;
	            for (Field field : fields) {
	            	if(AppConstants.JAVA_PRIMITIVE_WRAPPERS.contains(field.getType().toString())){
	                    Cell cell = row.createCell(colNum++);
	                    field.setAccessible(true);
	                    Object value = field.get(player);
	                    value = value == null ? "" : value.toString();
	                    cell.setCellValue(value.toString());
	                    cell.setCellStyle(regularStyle);
	            	}
	            }
	        }
		}

        String filename = "nfldraft_export/player_export_" + auth.getName() + "_" + new Date().getTime() + ".xls";
        try {
            FileOutputStream outputStream = new FileOutputStream("/usr/local/var/www/" + filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseException(new CustomErrorMessage("server error","error writing file from db"));
        }

        return appProperties.getNginxHost() + filename;
	}
	
	private XSSFFont createFont(XSSFWorkbook wb, String fontName, boolean isBold, short fontSize){
		XSSFFont font= wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setFontName(fontName);
		font.setBold(isBold);
	    return font;
	}
	
}
