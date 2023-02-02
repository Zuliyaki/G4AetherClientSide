/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.*;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author unaib
 */
public interface DailyNotesInterface {

    public void create_XML(Object requestEntity) throws CreateException;

    public void edit_XML(Object requestEntity) throws UpdateException;

    public void remove(String id) throws DeleteException;

    public <T> T findAllDailyNotes_XML(GenericType<T> responseType) throws DailyNoteNotFoundException;

    public <T> T findPatientEditedDailyNotes_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException;

    public <T> T findPatientNotesBetweenDayScores_XML(GenericType<T> responseType, String patientId, Double dayScoreLow, Double dayScoreGreat) throws DailyNoteNotFoundException;

    public <T> T findPatientDailyNotesByNotReadable_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException;

    public <T> T findDailyNoteById_XML(GenericType<T> responseType, String id) throws DailyNoteNotFoundException;

    public <T> T findPatientDailyNotesBetweenDates_XML(GenericType<T> responseType, String patientId, String dateLow, String dateGreat) throws DailyNoteNotFoundException;

    public <T> T findPatientDailyNoteByDate_XML(GenericType<T> responseType, String patientId, String date) throws DailyNoteNotFoundException;

    public <T> T findAllDailyNotesByPatientId_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException;
}
