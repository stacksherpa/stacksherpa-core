import React, { useState, useEffect, useCallback } from "react";

import styled from "styled-components";

import { BASE } from "../../../Constants";

const fetchLogs = (id, step) => {
  return fetch(`${BASE}/invocations/${id}/steps/${step}/logs`, {
    mode: "cors",
  }).then((response) => response.json());
};

const Console = styled.div`
  background: black;
  color: white;
  font-weight: 600;
  padding: 1rem;
  margin-bottom: 1rem;
  font-family: monospace;
  white-space: pre;
`;

const InvocationStep = ({ invocation, step }) => {
  const [logs, setLogs] = useState([]);

  const refresh = useCallback(() => {
    fetchLogs(invocation.id, step.id).then(setLogs);
  }, [invocation, step]);

  useEffect(() => {
    const interval = setInterval(refresh, 2000);
    return () => clearInterval(interval);
  }, [refresh]);

  return (
    <div>
      <Console>{logs.join("\n")}</Console>
    </div>
  );
};
export default InvocationStep;
