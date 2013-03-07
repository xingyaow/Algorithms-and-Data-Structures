clear all; clc;
DaffyDonaldData = 30:44;

DaffyTime = [0.012020138, 0.007500027, 0.012916307, 0.016290029...
    , 0.026181766, 0.042365933, 0.065456145, 0.10833599, 0.16849904...
    , 0.27145147, 0.457575939, 0.7255536, 1.182206038, 1.896166029, 3.049709669];
DonaldTime = [2.8486E-5, 8.468E-6, 7.314E-6, 6.545E-6, 7.699E-6, 7.314E-6...
    , 7.699E-6, 8.084E-6, 8.469E-6, 7.699E-6, 7.314E-6, 8.084E-6...
    , 8.854E-6, 8.084E-6, 8.854E-6];

MickyData = [1000,2000,4000,8000,16000,32000,64000,128000];
MickyTime = [3.0797E-5, 3.734E-5, 9.4314E-5, 1.85547E-4, 4.5809E-5...
    , 2.4637E-5, 4.4269E-5, 1.35118E-4];

MinnieTime = [0.003524239, 0.003021876, 0.011396129, 0.046703191...
    , 0.079294028, 0.72648018, 2.929924042, 12.040779544];

GoofyTime = [0.004502403, 6.59809E-4, 0.003015332, 0.011915046...
    , 0.044604431, 0.18118553, 0.721153979, 2.916120035];

PlutoTime = [5.14681E-4, 2.72932E-4, 5.01208E-4, 0.00104938, 0.001244551...
    , 0.002715839, 0.005725782, 0.012031686];

GyroData = [1000,2000,4000,8000,16000,32000,64000];
GyroTime = [1.8478E-5, 4.62E-6, 5.004E-6, 5.005E-6, 4.234E-6, 4.619E-6...
    , 4.62E-6];

BigIntegerTime = [0.008586747, 0.010763653, 0.036401866, 0.095877006...
    , 0.397636162, 1.730893659, 7.386571091];


BigO = MinnieTime./MickyData

% DData = log(DaffyDonaldData)
% 
% DaffyLogTime = log(DaffyTime)
% DonaldLogTime = log(DonaldTime)


% MData = log(MickyData)
% 
% MickyLogTime = log(MickyTime)
% MinnieLogTime = log(MinnieTime)
% GoofyLogTime = log(GoofyTime)
% PlutoLogTime = log(PlutoTime)
% 
% GData = log(GyroData)
% 
% GyroLogTime = log(GyroTime)
% BigIntegerLogTime = log(BigIntegerTime)


% figure('Name', 'Daffy')
% loglog(DaffyDonaldData,DaffyTime);
% set(gca, 'XLim', [10e0 10e5], 'YLim', [10e-5 10e0]);
% 
% figure('Name', 'Donald')
% loglog(DaffyDonaldData,DonaldTime);
% set(gca, 'XLim', [10e0 10e5], 'YLim', [10e-5 10e0]);

% figure('Name', 'Micky')
% loglog(MickyData,MickyTime);
% set(gca, 'XLim', [10e0 10e7], 'YLim', [10e-6 10e1]);
% 
% figure('Name', 'Minnie')
% loglog( MickyData,MinnieTime);
% set(gca, 'XLim', [10e0 10e7], 'YLim', [10e-6 10e1]);
% 
% figure('Name', 'Goofy')
% loglog(MickyData,GoofyTime);
% set(gca, 'XLim', [10e0 10e7], 'YLim', [10e-6 10e1]);
% % 
% figure('Name', 'Pluto')
% loglog(MickyData,PlutoTime);
% set(gca, 'XLim', [10e0 10e7], 'YLim', [10e-6 10e1]);
% % 
% figure('Name', 'Gyro')
% loglog(GyroData,GyroTime);
% %set(gca, 'XLim', [10e3 10e5], 'YLim', [10e-6 10e-4]);
% 
% figure('Name', 'BigInteger')
% loglog(GyroData,BigIntegerTime);
% set(gca, 'XLim', [10e3 10e5], 'YLim', [10e-1 10e1]);

