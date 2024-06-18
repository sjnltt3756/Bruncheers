import React, { useState } from "react";
import "../css/starRating.css";

const MakeStar = ({ onSelectRating , initialRating}) => {
  const [selectedRating, setSelectedRating] = useState(null);

  return (
    <div className="text-center py-20">
      <div className="flex justify-center items-center star-container">
        {[1, 2, 3, 4, 5].map((rating) => (
          <svg
            key={rating}
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            strokeWidth="2"
            stroke=""
            className="w-8 h-8 mt-2 flex"
            onMouseEnter={() => setSelectedRating(rating)}
            onClick={() => onSelectRating(rating)}
            fill={rating <= ( selectedRating || initialRating) ? "orange" : "lightgray"}
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M11.48 3.499a.562.562 0 0 1 1.04 0l2.125 5.111a.563.563 0 0 0 .475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 0 0-.182.557l1.285 5.385a.562.562 0 0 1-.84.61l-4.725-2.885a.562.562 0 0 0-.586 0L6.982 20.54a.562.562 0 0 1-.84-.61l1.285-5.386a.562.562 0 0 0-.182-.557l-4.204-3.602a.562.562 0 0 1 .321-.988l5.518-.442a.563.563 0 0 0 .475-.345L11.48 3.5Z"
            />
          </svg>
        ))}
      </div>
    </div>
  );
};

export default MakeStar;
