function CouponItem({coupon, oPrice}) {
    
  return (
    <tr>
    <td className="left">
      <input type="radio" className="coup_radio" name="coup_radio" />
      <span className="coup_span">{coupon.coupName}</span>
    </td>
    <td>{coupon.coupDesc}</td>
    <td>{Math.round(coupon.coupdiscount * 100)}%</td>
    <td className="discount_price">{(Math.floor(oPrice * coupon.coupdiscount)).toLocaleString()}원</td>
  </tr>
  )
}

export default CouponItem