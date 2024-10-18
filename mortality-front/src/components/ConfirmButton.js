import React from 'react';

const ConfirmButton = ({ isValid, errorMessage, onClick }) => {
    return (
        <button
            type="button"
            className="btn btn-primary"
            disabled={!isValid}
            title={!isValid ? errorMessage : ''}
            onClick={onClick}
        >
            Confirm
        </button>
    );
};

export default ConfirmButton;