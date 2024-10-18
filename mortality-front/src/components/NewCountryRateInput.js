import React, { useState } from 'react';
import RateInput from './RateInput';
import ConfirmButton from './ConfirmButton';

const NewCountryRateInput = ({ onConfirm, onCancel }) => {
  const [newRow, setNewRow] = useState({ countryCode: '', maleRate: '', femaleRate: '' });
  const [isValid, setIsValid] = useState(false);

  const handleNewRowChange = (e) => {
    const { name, value } = e.target;
    setNewRow((prevRow) => ({ ...prevRow, [name]: value }));
    validateForm({ ...newRow, [name]: value });
  };

  const validateForm = (row) => {
    const isValid = row.countryCode && row.maleRate && row.femaleRate;
    setIsValid(isValid);
  };

  const handleConfirm = () => {
    onConfirm(newRow);
  };

  return (
    <tr>
      <td>
        <input
          type="text"
          className="form-control"
          name="countryCode"
          placeholder="Country Code"
          value={newRow.countryCode}
          onChange={handleNewRowChange}
        />
      </td>
      <td>
        <RateInput
          name="maleRate"
          value={newRow.maleRate}
          onChange={handleNewRowChange}
          setIsValid={setIsValid}
        />
      </td>
      <td>
        <RateInput
          name="femaleRate"
          value={newRow.femaleRate}
          onChange={handleNewRowChange}
          setIsValid={setIsValid}
        />
      </td>
      <td>
        <ConfirmButton
          isValid={isValid}
          errorMessage="All fields must be completed"
          onClick={handleConfirm}
        />
        <button className="btn btn-secondary" onClick={onCancel}>Cancel</button>
      </td>
    </tr>
  );
};

export default NewCountryRateInput;