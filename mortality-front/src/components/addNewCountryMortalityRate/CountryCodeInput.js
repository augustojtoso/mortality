import React, { useState } from 'react';
import isoCountries from 'i18n-iso-countries';

const CountryCodeInput = ({ value, onChange, allCountries }) => {
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { value } = e.target;
    if (value.length > 2) {
      setError('Country code must be exactly 2 characters long');
    } else if (!isoCountries.isValid(value)) {
      setError('Invalid ISO country code');
    } else if (allCountries.includes(value)) {
        setError('Country already present, use edit to update');
    } else {
      setError('');
    }
    onChange(e);
  };

return (
    <div>
        <input
            type="text"
            className="form-control"
            name="countryCode"
            placeholder="Country Code"
            value={value.toUpperCase()}
            onChange={(e) => {
                e.target.value = e.target.value.toLowerCase();
                handleChange(e);
            }}
            maxLength="2"
        />
        {error && <div className="text-danger">{error}</div>}
    </div>
);
};

export default CountryCodeInput;