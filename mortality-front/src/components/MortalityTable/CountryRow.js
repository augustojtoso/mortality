import React, { useState } from 'react';
import isoCountries from 'i18n-iso-countries';
import RateInput from '../common/RateInput';
import ConfirmButton from '../common/ConfirmButton';

const CountryRow = ({ countryCode, mortalityRate, onConfirm }) => {
  const [editMode, setEditMode] = useState(false);
  const [newRow, setLocalEditData] = useState({ ...mortalityRate, countryCode });
  const [isValid, setIsValid] = useState(true);

  isoCountries.registerLocale(require("i18n-iso-countries/langs/en.json"));

  const getCountryName = (countryCode) => isoCountries.getName(countryCode, "en");

  const handleConfirm = async () => {
    onConfirm(newRow)
    setEditMode(false);
  };

  const handleCancel = () => {
    setEditMode(false);
    setLocalEditData({ ...mortalityRate, countryCode });
  };

  const handleLocalChange = (e) => {
    setLocalEditData({ ...newRow, [e.target.name]: e.target.value });
  };

  return (
    <tr key={countryCode}>
      <td>{getCountryName(countryCode)}</td>
      <td>
        {editMode ? (
          <RateInput
            name="maleRate"
            value={newRow.maleRate}
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
            value={newRow.femaleRate}
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
          <button className="btn btn-primary" onClick={() => setEditMode(true)}>Edit</button>
        )}
      </td>
    </tr>
  );
};

export default CountryRow;