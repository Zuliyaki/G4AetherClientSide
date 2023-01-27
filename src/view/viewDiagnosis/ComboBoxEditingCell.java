/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import entities.Diagnosis;
import entities.MentalDisease;
import interfaces.MentalDiseaseInterface;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javax.ws.rs.core.GenericType;
import restful.MentalDiseaseRestful;

/**
 *
 * @author 2dam
 */

    public class ComboBoxEditingCell extends TableCell<Diagnosis, MentalDisease> {
            private MentalDiseaseInterface mentalDiseaseInterface = new MentalDiseaseRestful();


        private ComboBox<MentalDisease> comboBox;
        
        ComboBoxEditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createComboBox();
                setText(null);
                setGraphic(comboBox);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getTyp().getMdName());
            setGraphic(null);
        }

        @Override
        public void updateItem(MentalDisease item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (comboBox != null) {
                        comboBox.setValue(getTyp());
                    }
                    setText(getTyp().getMdName());
                    setGraphic(comboBox);
                } else {
                    setText(getTyp().getMdName());
                    setGraphic(null);
                }
            }
        }

        private void createComboBox() {
            
            comboBox = new ComboBox<>();
            
            comboBoxConverter(comboBox);
            comboBox.valueProperty().set(getTyp());
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction((e) -> {
                System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });
//            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                if (!newValue) {
//                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
//                }
//            });
        }

        private void comboBoxConverter(ComboBox<MentalDisease> comboBox) {
            // Define rendering of the list of values in ComboBox drop down. 
            
           
            comboBox.setCellFactory((c) -> {
                return new ListCell<MentalDisease>() {
                    @Override
                    protected void updateItem(MentalDisease item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getMdName());
                        }
                    }
                };
            });
        }

        private MentalDisease getTyp() {
            return getItem() == null ? new MentalDisease() : getItem();
        }
    }

    

