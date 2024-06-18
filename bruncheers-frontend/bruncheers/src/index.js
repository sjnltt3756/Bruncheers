import { BrowserRouter } from "react-router-dom";
import App from "./App";
import ReactDOM from "react-dom/client";
import { RecoilRoot } from "recoil";

const root = ReactDOM.createRoot(document.getElementById("container"));
root.render(
  <BrowserRouter>
    <RecoilRoot>
      <App />
    </RecoilRoot>
  </BrowserRouter>
);
