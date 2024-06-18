import React, { useState, useEffect } from 'react';

function CustomCheckbox({ checked, onChange }) {
  const [isChecked, setIsChecked] = useState(checked);

  // 부모 컴포넌트로부터 전달된 checked prop이 변경될 때마다 isChecked 상태를 업데이트
  useEffect(() => {
    setIsChecked(checked);
  }, [checked]);

  const checkedImage = './image/checkbox_checked.png'; // 체크된 이미지 URL
  const uncheckedImage = './image/checkbox_unchecked.png'; // 체크되지 않은 이미지 URL

  const toggleCheckbox = () => {
    const newChecked = !isChecked;
    setIsChecked(newChecked);
    onChange && onChange(newChecked);
  };

  return (
    <div className="CustomCheckbox" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <img src={isChecked ? checkedImage : uncheckedImage} alt="Checkbox" onClick={toggleCheckbox} style={{ cursor: 'pointer' ,width: '18px', height: '18px'}} />
    </div>
  );
}

export default CustomCheckbox;