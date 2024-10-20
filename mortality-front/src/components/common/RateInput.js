import React, { useState } from 'react';

const RateInput = ({ name, value, onChange, setIsValid }) => {
    const [errorMessage, setErrorMessage] = useState('');

    const handleChange = (e) => {
        const newValue = e.target.value;
        const regex = /^\d{0,3}(\.\d{0,2})?$/;

        if (newValue < 0) {
            setErrorMessage('Value must be positive');
            setIsValid(false);
        } else if (newValue >= 1000) {
            setErrorMessage('Value must be less than 1000');
            setIsValid(false);
        } else if (!regex.test(newValue)) {
            setErrorMessage('Value must have up to 2 decimal places.');
            setIsValid(false);
        } else {
            setErrorMessage('');
            setIsValid(true);
        }
        onChange(e);
    };


    return (
        <div>
            <input
                type="number"
                className="form-control"
                name={name}
                value={value}
                onChange={handleChange}
                min="0"
                max="999.99"
                step="0.01"
            />
            {errorMessage && <div className="error-message">{errorMessage}</div>}
        </div>
    );
};

export default RateInput;