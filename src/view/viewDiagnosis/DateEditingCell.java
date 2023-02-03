/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;
import entities.Diagnosis;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;



/**
 * 
 * @author 2dam
 */


 public class DateEditingCell extends TableCell<Diagnosis, Date> {

        private DatePicker datePicker;
/**
 * this is the defaut constructor
 */
        DateEditingCell() {
                          
                    
        }
        /**
         * on start editing
         */
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createDatePicker();
                setText(null);
                setGraphic(datePicker);
            }
        }
        /**
         * on cancel edit
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getDate().toString());
            setGraphic(null);
        }
        /**
         * this method update the date
         * @param item the date to edit
         * @param empty the boolean to check if its empty
         */
        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (datePicker != null) {
                        datePicker.setValue(getDate());
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                    final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    setText(getDate().format(dateFormat));
                    
                    setGraphic(null);
                    
                }
            }
        }
        /**
         * create the datepciker
         */
        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                System.out.println("Committed: " + datePicker.getValue().toString());
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                
            });   
            
  
        

        }
       

        /**
         * get the current date
         * @return the date formated
         */
        private LocalDate getDate() {
            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }