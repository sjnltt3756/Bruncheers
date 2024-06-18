import React, { useState } from 'react';


function QuantityCounter() {
  const [quantity, setQuantity] = useState(0);

  const incrementQuantity = () => {
    setQuantity(quantity + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 0) {
      setQuantity(quantity - 1);
    }
  };


  return (
    <div className='qbtn-div'>
      <button className='qbtn' onClick={decrementQuantity}>-</button>
      <span style={{padding:'3px'}}>{quantity}</span>
      <button className='qbtn' onClick={incrementQuantity}>+</button>
    </div>
  );
}

export default QuantityCounter;