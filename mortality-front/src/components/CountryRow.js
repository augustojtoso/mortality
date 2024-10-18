import React, { useState } from 'react';
import isoCountries from 'i18n-iso-countries';
import axios from 'axios';
import RateInput from './RateInput';
import ConfirmButton from './ConfirmButton';

const CountryRow = ({ countryCode, mortalityRate, editCountryCode, editData, handleChange, fetchData, year }) => {
  const [editMode, setEditMode] = useState(false);
  const [localEditData, setLocalEditData] = useState(mortalityRate);
  const [isValid, setIsValid] = useState(true);

  isoCountries.registerLocale(require("i18n-iso-countries/langs/en.json"));

  const getCountryName = (countryCode) => {
    return isoCountries.getName(countryCode, "en");
  };

  const handleEdit = () => {
    setEditMode(true);
  };

  const handleConfirm = async () => {
    try {
      await axios.post(`/mortalityrates/years/${year}/countries/${countryCode}`, localEditData);
      fetchData(year);
    } catch (error) {
      console.error('Error updating data:', error);
    }
    setEditMode(false);
  };

  const handleCancel = () => {
    setEditMode(false);
    setLocalEditData(mortalityRate);
  };

  const handleLocalChange = (e) => {
    setLocalEditData({ ...localEditData, [e.target.name]: e.target.value });
  };

  return (
    <tr key={countryCode}>
      <td>{getCountryName(countryCode)}</td>
      <td>
        {editMode ? (
          <RateInput
            name="maleRate"
            value={localEditData.maleRate}
            onChange={handleLocalChange}
            setIsValid={setIsValid}
          />
        ) : (
          mortalityRate.maleRate
        )}
      </td>
      <td>
        {editMode ? (
          <RateInput
            name="femaleRate"
            value={localEditData.femaleRate}
            onChange={handleLocalChange}
            setIsValid={setIsValid}
          />
        ) : (
          mortalityRate.femaleRate
        )}
      </td>
      <td>
        {editMode ? (
          <>
            <ConfirmButton isValid={isValid} onClick={handleConfirm} />
            <button className="btn btn-secondary" onClick={handleCancel}>Cancel</button>
          </>
        ) : (
          <button className="btn btn-primary" onClick={handleEdit}>Edit</button>
        )}
      </td>
    </tr>
  );
};

export default CountryRow;