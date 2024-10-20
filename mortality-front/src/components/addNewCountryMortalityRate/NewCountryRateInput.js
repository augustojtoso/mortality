import React, { useState } from 'react';
import RateInput from '../common/RateInput';
import ConfirmButton from '../common/ConfirmButton';
import CountryCodeInput from './CountryCodeInput';

const NewCountryRateInput = ({ onConfirm, onCancel, allCountries }) => {
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

    return (
        <tr>
            <td>
                <CountryCodeInput
                    value={newRow.countryCode}
                    onChange={handleNewRowChange}
                    allCountries={allCountries}
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
                    onClick={() => onConfirm(newRow)}
                />
                <button className="btn btn-secondary" onClick={onCancel}>Cancel</button>
            </td>
        </tr>
    );
};

export default NewCountryRateInput;